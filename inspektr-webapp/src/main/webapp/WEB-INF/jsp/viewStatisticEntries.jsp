<%@page import="org.inspektr.webapp.domain.StatisticSearchRequest"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.inspektr.statistics.annotation.Statistic.Precision"%>
<%@include file="includes/top.jsp"%>

<form method="get" action="viewStatisticEntries.html" style="width:100%; display: block;" class="full">
<p>
<label for="applicationCode">Application:</label> <select name="applicationCode">
<c:forEach items="${applicationCodes}" var="item">
	<option${item eq statisticSearchRequest.applicationCode ? ' selected="selected"' : ''}>${item}</option>
</c:forEach>
</select>

<fmt:formatDate value="${statisticSearchRequest.startDate}" pattern="yyyy-MM-dd " var="dateFrom" />
<fmt:formatDate value="${statisticSearchRequest.endDate}" pattern="yyyy-MM-dd" var="dateTo" />
<label for="startDate">From </label> <input name="startDate" value="${dateFrom}" />
<label for="endDate">to </label>  <input name="endDate" value="${dateTo}" />

Precision: 
<select name="requiredPrecisions" multiple="multiple">
<c:forEach items="${precisions}" var="precision">
	<option>${precision}</option>
</c:forEach>
</select>

<input type="submit" value="DATE_RANGE" /></p>
</form>

<br style="clear:both" />

<table>
	<thead>
		<tr>
			<td>Application Code</td>
			<td>When</td>
			<td>What</td>
			<td>Count</td>
			<td>Level of Precision</td>
		</tr>
	</thead>
	<tbody>
<c:forEach items="${statistics}" var="statistic">
		<tr>
			<td>${statistic.applicationCode}</td>
			<td>${statistic.when}</td>
			<td>${statistic.what}</td>
			<td>${statistic.count}</td>
			<td>${statistic.precision}</td>
		</tr>
</c:forEach>
	</tbody>
</table>	

<%@include file="includes/bottom.jsp"%>