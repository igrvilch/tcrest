<#list courses as course>
    <#if !chosenCategory?has_content || chosenCategory == "All" || course.courseCategory.categoryName == chosenCategory>
        <#if course.courseState != "DRAFT" && course.courseState != "PROPOSAL" && course.courseState != "REJECTED">
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
            <#if !course.subscribers?seq_contains(user.userEmail)
            && !course.attenders?seq_contains(user.userEmail)
            && !course.evaluators?seq_contains(user.userEmail)
            && (course.courseState == "NEW" || course.courseState == "OPEN" || course.courseState == "READY")>
                <td>
                    <a class="btn btn-mini"
                       href="${rc.getContextPath()}/courses/${course.courseId}/subscribe">Subscribe</a>
                </td>
            </#if>
            <#if course.subscribers?seq_contains(user.userEmail)
            && course.courseState == "NEW">
                <td></td>
            </#if>
            <#if course.subscribers?seq_contains(user.userEmail) && (course.courseState == "OPEN" || course.courseState == "READY")>
                <td>
                    <a class="btn btn-mini"
                       href="${rc.getContextPath()}/courses/${course.courseId}/attend">Attend</a>
                </td>
            </#if>
            <#if course.attenders?seq_contains(user.userEmail) && course.courseState == "FINISHED">
                <td>
                    <a class="btn btn-mini"
                       href="${rc.getContextPath()}/courses/${course.courseId}/evaluate">Evaluate</a>
                </td>
            </#if>
            <#if (course.attenders?seq_contains(user.userEmail) && course.courseState != "FINISHED") ||
            ((course.evaluators?seq_contains(user.userEmail) || course.subscribers?seq_contains(user.userEmail))
            && course.courseState == "FINISHED") || course.courseState == "INPROGRESS" ||
            (!course.subscribers?seq_contains(user.userEmail) && !course.attenders?seq_contains(user.userEmail) &&
            !course.evaluators?seq_contains(user.userEmail) && course.courseState == "FINISHED")>
                <td></td>
            </#if>
        </tr>
        </#if>
    </#if>
</#list>