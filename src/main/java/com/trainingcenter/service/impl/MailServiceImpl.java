package com.trainingcenter.service.impl;

import com.trainingcenter.model.Course;
import com.trainingcenter.model.User;
import com.trainingcenter.service.CoursesService;
import com.trainingcenter.service.MailService;
import freemarker.template.TemplateException;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    MailSender mailSender;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    CoursesService coursesService;

    @Override
    public void sendMail(SimpleMailMessage message) {
        mailSender.send(message);
    }

    @Override
    public void sendCourseAnnouncement(Course course, User user, HttpServletRequest request) throws IOException, TemplateException {
        SimpleMailMessage message = new SimpleMailMessage();
        String[] managers = {"km.trcr2014@mail.ru", "dm.trcr2014@mail.ru"};
        message.setTo(managers);
        message.setFrom("notification.trcr2014@mail.ru");
        message.setCc(user.getUserEmail());
        StringBuffer text = new StringBuffer();
        Map<String, String> model = new HashMap<String, String>();
        model.put("CourseName", course.getCourseName());
        model.put("CourseCategory", course.getCourseCategory().getCategoryName());
        model.put("CourseDescription", course.getCourseDescription());
        model.put("CourseLinks", course.getCourseLinks());
        model.put("LectorName", user.getUserLogin());
        model.put("context", request.getRequestURL().toString().replace("sendtoreview", "approve"));
        text.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration()
                .getTemplate("courseAnnouncement.ftl"), model));
        message.setText(String.valueOf(text));
        message.setSubject("Course Announcement");
        sendMail(message);
    }

    @Override
    public void sendCourseApprovalUpdate(Course course, User user) throws IOException, TemplateException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(course.getCourseOwner().getUserEmail());
        message.setFrom("notification.trcr2014@mail.ru");
        String[] managers = {"km.trcr2014@mail.ru", "dm.trcr2014@mail.ru"};
        message.setCc(managers);
        StringBuffer text = new StringBuffer();
        Map<String, String> model = new HashMap<String, String>();
        model.put("CourseName", course.getCourseName());
        model.put("ManagerName", user.getUserLogin());
        model.put("CourseLecturer", course.getCourseOwner().getUserLogin());
        if (user.getUserRole().equals(User.UserRole.DM)) {
            model.put("ManagerDecision", course.getDmDecision());
        }
        if (user.getUserRole().equals(User.UserRole.KM)) {
            model.put("ManagerDecision", course.getKmDecision());
        }
        if (user.getUserRole().equals(User.UserRole.DM)) {
            model.put("ManagerReason", course.getDmReason());
        }
        if (user.getUserRole().equals(User.UserRole.KM)) {
            model.put("ManagerReason", course.getKmReason());
        }
        text.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration()
                .getTemplate("courseApprovalUpdate.ftl"), model));
        message.setText(String.valueOf(text));
        message.setSubject("Course Approval Update");
        sendMail(message);
    }

    @Override
    public void sendNewCourseAdded(Course course, User user, HttpServletRequest request) throws IOException, TemplateException {
        SimpleMailMessage message = new SimpleMailMessage();
        String[] users = {"user-a.trcr2014@mail.ru", "user-b.trcr2014@mail.ru", "user-c.trcr2014@mail.ru"};
        message.setTo(users);
        String[] cc = {"km.trcr2014@mail.ru", "dm.trcr2014@mail.ru", course.getCourseOwner().getUserEmail()};
        message.setFrom("notification.trcr2014@mail.ru");
        message.setCc(cc);
        StringBuffer text = new StringBuffer();
        Map<String, String> model = new HashMap<String, String>();
        model.put("CourseName", course.getCourseName());
        model.put("CourseCategory", course.getCourseCategory().getCategoryName());
        model.put("CourseDescription", course.getCourseDescription());
        model.put("CourseLinks", course.getCourseLinks());
        model.put("LectorName", user.getUserLogin());
        model.put("CourseDetailLink", request.getRequestURL().toString().replace("approve", ""));
        model.put("CourseSubscribeLink", request.getRequestURL().toString().replace("approve", "subscribe"));
        text.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration()
                .getTemplate("newCourseAdded.ftl"), model));
        message.setText(String.valueOf(text));
        message.setSubject("New Course Added");
        sendMail(message);
    }

    @Override
    public void sendCourseRejected(Course course, User user, HttpServletRequest request) throws IOException, TemplateException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(course.getCourseOwner().getUserEmail());
        message.setFrom("notification.trcr2014@mail.ru");
        String[] managers = {"km.trcr2014@mail.ru", "dm.trcr2014@mail.ru"};
        message.setCc(managers);
        StringBuffer text = new StringBuffer();
        Map<String, String> model = new HashMap<String, String>();
        if (!course.getDmDecision().equals("Select one")) {
            model.put("ManagerDecision1", course.getDmDecision());
        }
        if (!course.getKmDecision().equals("Select one")) {
            model.put("ManagerDecision2", course.getKmDecision());
        }
        if (!(course.getDmReason() == null)) {
            model.put("ManagerReason1", course.getDmReason());
        }
        if (!(course.getDmReason() == null)) {
            model.put("ManagerReason2", course.getKmReason());
        }
        model.put("CourseUpdateLink", request.getRequestURL().toString().replace("approve", ""));
        if (user.getUserRole() == User.UserRole.DM && (course.getKmDecision().equals("Select one") ||
                course.getKmDecision().equals("Approve"))) {
            model.put("voteCount", "one manager");
        } else if (user.getUserRole() == User.UserRole.KM && (course.getDmDecision().equals("Select one") ||
                course.getDmDecision().equals("Approve"))) {
            model.put("voteCount", "one manager");
        } else if (user.getUserRole() == User.UserRole.DM && course.getKmDecision().equals("Reject")) {
            model.put("voteCount", "both managers");
        } else if (user.getUserRole() == User.UserRole.KM && course.getDmDecision().equals("Reject")) {
            model.put("voteCount", "both managers");
        }
        text.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration()
                .getTemplate("courseRejected.ftl"), model));
        message.setText(String.valueOf(text));
        message.setSubject("Course Rejected");
        sendMail(message);
    }

    @Override
    public void sendCourseDeleted(Course course, User user) throws IOException, TemplateException {
        SimpleMailMessage message = new SimpleMailMessage();
        String[] managers = {"km.trcr2014@mail.ru", "dm.trcr2014@mail.ru"};
        message.setTo(managers);
        message.setFrom("notification.trcr2014@mail.ru");
        message.setCc(user.getUserEmail());
        StringBuffer text = new StringBuffer();
        Map<String, String> model = new HashMap<String, String>();
        model.put("CourseName", course.getCourseName());
        model.put("CourseCategory", course.getCourseCategory().getCategoryName());
        model.put("CourseDescription", course.getCourseDescription());
        model.put("CourseLinks", course.getCourseLinks());
        model.put("LecturerName", user.getUserLogin());
        if (!course.getDmDecision().equals("Select one")) {
            model.put("ManagerDecision1", course.getDmDecision());
        }
        if (!course.getKmDecision().equals("Select one")) {
            model.put("ManagerDecision2", course.getKmDecision());
        }
        if (!(course.getDmReason() == null)) {
            model.put("ManagerReason1", course.getDmReason());
        }
        if (!(course.getDmReason() == null)) {
            model.put("ManagerReason2", course.getKmReason());
        }
        text.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration()
                .getTemplate("courseDeleted.ftl"), model));
        message.setText(String.valueOf(text));
        message.setSubject("Course Deleted");
        sendMail(message);
    }

    @Override
    public void sendCourseOpen(Course course, User user, HttpServletRequest request) throws IOException, TemplateException {
        SimpleMailMessage message = new SimpleMailMessage();
        String[] users = (String[]) ArrayUtils.add(course.getSubscribers().toArray(new String[course.getSubscribers().size()]), user.getUserEmail());
        message.setTo(users);
        message.setFrom("notification.trcr2014@mail.ru");
        message.setCc(course.getCourseOwner().getUserEmail());
        StringBuffer text = new StringBuffer();
        Map<String, String> model = new HashMap<String, String>();
        model.put("CourseName", course.getCourseName());
        model.put("CourseCategory", course.getCourseCategory().getCategoryName());
        model.put("CourseDescription", course.getCourseDescription());
        model.put("CourseLinks", course.getCourseLinks());
        model.put("LecturerName", course.getCourseOwner().getUserLogin());
        model.put("CourseDetailLink", request.getRequestURL().toString().replace("subscribe", ""));
        model.put("CourseAttenderLink", request.getRequestURL().toString().replace("subscribe", "attend"));
        text.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration()
                .getTemplate("courseOpen.ftl"), model));
        message.setText(String.valueOf(text));
        message.setSubject("Course Open");
        sendMail(message);
    }

    @Override
    public void sendCourseReady(Course course, User user, HttpServletRequest request) throws IOException, TemplateException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(course.getCourseOwner().getUserEmail());
        message.setFrom("notification.trcr2014@mail.ru");
        StringBuffer text = new StringBuffer();
        Map<String, String> model = new HashMap<String, String>();
        model.put("CourseName", course.getCourseName());
        model.put("CourseCategory", course.getCourseCategory().getCategoryName());
        model.put("CourseDescription", course.getCourseDescription());
        model.put("CourseLinks", course.getCourseLinks());
        model.put("LecturerName", course.getCourseOwner().getUserLogin());
        model.put("CourseDetailLink", request.getRequestURL().toString().replace("attend", ""));
        text.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration()
                .getTemplate("courseReady.ftl"), model));
        message.setText(String.valueOf(text));
        message.setSubject("Course Ready to Start");
        sendMail(message);
    }

    @Override
    public void sendCourseStarted(Course course, User user, HttpServletRequest request) throws IOException, TemplateException {
        SimpleMailMessage message = new SimpleMailMessage();
        String[] users = (String[]) ArrayUtils.add(course.getAttenders().toArray(new String[course.getSubscribers().size()]), user.getUserEmail());
        message.setTo(users);
        message.setFrom("notification.trcr2014@mail.ru");
        message.setCc(course.getCourseOwner().getUserEmail());
        StringBuffer text = new StringBuffer();
        Map<String, String> model = new HashMap<String, String>();
        model.put("CourseName", course.getCourseName());
        model.put("CourseCategory", course.getCourseCategory().getCategoryName());
        model.put("CourseDescription", course.getCourseDescription());
        model.put("CourseLinks", course.getCourseLinks());
        model.put("CourseDetailLink", request.getRequestURL().toString().replace("start", ""));
        text.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration()
                .getTemplate("courseStarted.ftl"), model));
        message.setText(String.valueOf(text));
        message.setSubject("Course Started");
        sendMail(message);
    }

    @Override
    public void sendCourseFinished(Course course, User user, HttpServletRequest request) throws IOException, TemplateException {
        SimpleMailMessage message = new SimpleMailMessage();
        String[] users = course.getAttenders().toArray(new String[course.getSubscribers().size()]);
        message.setTo(users);
        message.setFrom("notification.trcr2014@mail.ru");
        message.setCc(course.getCourseOwner().getUserEmail());
        StringBuffer text = new StringBuffer();
        Map<String, String> model = new HashMap<String, String>();
        model.put("CourseName", course.getCourseName());
        model.put("CourseCategory", course.getCourseCategory().getCategoryName());
        model.put("CourseDescription", course.getCourseDescription());
        model.put("CourseLinks", course.getCourseLinks());
        model.put("CourseDetailLink", request.getRequestURL().toString().replace("finish", ""));
        text.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration()
                .getTemplate("courseFinished.ftl"), model));
        message.setText(String.valueOf(text));
        message.setSubject("Course Finished");
        sendMail(message);
    }

    @Override
    public void sendCourseNotify(Course course, User user, HttpServletRequest request) throws IOException, TemplateException {
        SimpleMailMessage message = new SimpleMailMessage();
        List<String> notEvaluators = new ArrayList<String>();
        for (String attender : course.getAttenders()) {
            if (!course.getEvaluators().contains(attender)) {
                notEvaluators.add(attender);
            }
        }
        String[] users = notEvaluators.toArray(new String[course.getSubscribers().size()]);
        message.setTo(users);
        message.setFrom("notification.trcr2014@mail.ru");
        message.setCc(course.getCourseOwner().getUserEmail());
        StringBuffer text = new StringBuffer();
        Map<String, String> model = new HashMap<String, String>();
        model.put("CourseName", course.getCourseName());
        model.put("CourseCategory", course.getCourseCategory().getCategoryName());
        model.put("CourseDescription", course.getCourseDescription());
        model.put("CourseLinks", course.getCourseLinks());
        model.put("CourseLink", request.getRequestURL().toString().replace("notify", "evaluate"));
        text.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration()
                .getTemplate("courseNotify.ftl"), model));
        message.setText(String.valueOf(text));
        message.setSubject("Evaluate Course, Please");
        sendMail(message);
    }
}
