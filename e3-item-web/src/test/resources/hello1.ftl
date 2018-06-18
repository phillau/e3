hello,${name!"name没有值"}
<#list students as student>
    ${student_index}-${student.name}-${student.age}
    <#if student_index==0>
        ${student.name}0
    <#else>
        ${student.name}1
    </#if>
</#list>
${date?date}
${date?string("yyyy/MM/dd")}