<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>cFrost.net</title>
<%@ include file="/WEB-INF/commonPages/header.jsp"%>
<div id="progress"
    style="margin-top: -1px; padding: 10px;">
    <p>This site is being built...</p>
    <%=request.getAttribute("count")%><br/>
    ${requestScope.process1}<br/>
    ${requestScope.process2}<br/>
    ${requestScope.process3}<br/>
    ${requestScope.process4}<br/>
    ${requestScope.process5}<br/>
    ${requestScope.process6}<br/>
    ${requestScope.process7}<br/>
    ${requestScope.process8}<br/>
    ${requestScope.process9}<br/>
    ${requestScope.process10}<br/>
</div>

<%@ include file="/WEB-INF/commonPages/footer.jsp"%>