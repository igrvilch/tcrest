<#list courses as course>
    <#if !chosenCategory?has_content || chosenCategory == "All" || course.courseCategory.categoryName == chosenCategory>
        <#if course.courseState != "DRAFT">
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
            <#if course.courseState == "PROPOSAL" || course.courseState == "REJECTED">
                <td>
                    <a class="btn btn-mini"
                       href="${rc.getContextPath()}/courses/${course.courseId}/approve">Approve</a>
                </td>
            </#if>
            <#if course.courseState != "PROPOSAL" && course.courseState != "REJECTED">
                <td></td>
            </#if>
        </#if>
    </#if>
</#list>