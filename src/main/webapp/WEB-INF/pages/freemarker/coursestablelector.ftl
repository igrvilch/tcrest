<#list courses as course>
    <#if !chosenCategory?has_content || chosenCategory == "All" || course.courseCategory.categoryName == chosenCategory>
        <#if course.courseOwner.userId == user.userId  ||
        (course.courseOwner.userId != user.userId && (course.courseState == "NEW" || course.courseState == "OPEN" ||
        course.courseState == "READY" || course.courseState == "INPROGRESS" || course.courseState == "FINISHED"))>
        <tr>
            <td>${course.courseId}</td>
            <td>
                <a href="${rc.getContextPath()}/courses/${course.courseId}">${course.courseName}</a>
                <span class="label">${course.courseState}</span>
            </td>
            <td>
            ${course.courseCategory.categoryName}
            </td>
            <#if !course.subscribers?has_content && !course.attenders?has_content &&
            !course.evaluators?has_content>
                <td></td>
            </#if>
            <#if course.subscribers?has_content || course.attenders?has_content || course.evaluators?has_content>
                <td>
                    <a href="${rc.getContextPath()}/courses/${course.courseId}/participants">${course.subscribers?size}
                        /${course.attenders?size + course.evaluators?size}</a></td>
            </#if>
            <#if course.gradeAvg == 0>
                <td></td>
            </#if>
            <#if course.gradeAvg != 0>
                <td>${course.gradeAvg}</td>
            </#if>
            <#if course.courseOwner.userId != user.userId ||
            (course.courseOwner.userId == user.userId && (course.courseState == "NEW" || course.courseState == "OPEN" || course.courseState == "PROPOSAL"))>
                <td></td>
            </#if>
            <#if course.courseOwner.userId == user.userId && course.courseState == "READY">
                <td>
                    <a class="btn btn-mini"
                       href="${rc.getContextPath()}/courses/${course.courseId}/start">Start</a>
                </td>
            </#if>
            <#if course.courseOwner.userId == user.userId && (course.courseState == "DRAFT" || course.courseState == "REJECTED")>
                <td>
                    <div class="btn-group">
                        <a class="btn btn-mini"
                           href="${rc.getContextPath()}/courses/${course.courseId}/update">Update</a>
                        <a class="btn dropdown-toggle btn-mini" data-toggle="dropdown" href="#"><span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="${rc.getContextPath()}/courses/${course.courseId}/sendtoreview">Send to
                                    Review</a>
                                <a href="${rc.getContextPath()}/courses/${course.courseId}/delete">Delete</a>
                            </li>
                        </ul>
                    </div>
                </td>
            </#if>
            <#if course.courseOwner.userId == user.userId && course.courseState == "INPROGRESS">
                <td>
                    <a class="btn btn-mini"
                       href="${rc.getContextPath()}/courses/${course.courseId}/finish">Finish</a>
                </td>
            </#if>
            <#if course.courseOwner.userId == user.userId && course.courseState == "FINISHED">
                <td>
                    <a class="btn btn-mini"
                       href="${rc.getContextPath()}/courses/${course.courseId}/notify">Notify</a>
                </td>
            </#if>
        </tr>
        </#if>
    </#if>
</#list>