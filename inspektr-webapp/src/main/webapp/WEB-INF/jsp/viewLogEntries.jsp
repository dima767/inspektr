<%@include file="includes/top.jsp"%>

<h2>Filter</h2>
<form method="get" action="viewLogEntries.html" style="width:100%; display: block;" class="full">
<p>
<label for="applicationCode">Application:</label> <select name="applicationCode">
<c:forEach items="${applicationCodes}" var="item">
	<option${item eq logSearchRequest.applicationCode ? ' selected="selected"' : ''}>${item}</option>
</c:forEach>
</select>

<label for="principal">Principal:</label> <input name="principal" value="${logSearchRequest.principal}" />

<fmt:formatDate value="${logSearchRequest.startDate}" pattern="yyyy-MM-dd HH:mm" var="dateFrom" />
<fmt:formatDate value="${logSearchRequest.endDate}" pattern="yyyy-MM-dd HH:mm" var="dateTo" />
<label for="startDate">From </label> <input name="startDate" value="${dateFrom}" />
<label for="endDate">to </label>  <input name="endDate" value="${dateTo}" />
<input type="submit" value="Filter" /></p>
</form>

<br style="clear:both" />

<table>
	<thead>
		<tr>
			<td>Date</td>
			<td>Application</td>
			<td>Principal</td>
			<td>Action</td>
			<td>Resource</td>
			<td>Client Ip Address</td>
			<td>Server Ip Address</td>
		</tr>
	</thead>
	<tbody>
<c:forEach items="${logEntries}" var="logEntry">
		<tr>
		
			<td><fmt:formatDate value="${logEntry.entryDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<td>${logEntry.applicationCode}</td>
			<td>${logEntry.principal}</td>
			<td>${logEntry.actionPerformed}</td>
			<td>${logEntry.resource}</td>
			<td>${logEntry.clientIpAddress}</td>
			<td>${logEntry.serverIpAddress}</td>
		</tr>
</c:forEach>
	</tbody>
</table>
<%@include file="includes/bottom.jsp"%>