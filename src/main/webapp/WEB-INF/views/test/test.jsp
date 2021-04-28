<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>
		<a href="${pageContext.request.contextPath}/language?language=ko">한국어</a>
		<a href="${pageContext.request.contextPath}/language?language=en">English</a>
		<%-- <a href="<c:url value='${pageContext.request.contextPath}/language?language=ko' />">한국어</a>
		<a href="<c:url value='${pageContext.request.contextPath}/language?language=en' />">English</a> --%>
	</p>

	<table border="1">
		<thead>
			<tr>
				<th>
					<p>test.id : <spring:message code="test.id" text="default text" /></p>
				</th>
				<th>
					<p>test.name : <spring:message code="test.name" text="default text" /></p>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty LIST }">
					<c:forEach items="${LIST }" var="data">
						<tr>
							<td>
								<c:choose>
									<c:when test="${not empty data.id }">
										${data.id }
									</c:when>
									<c:otherwise>
										N/A
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<a href="${pageContext.request.contextPath}/masterBooks/${data.id}">
									<c:choose>
										<c:when test="${not empty data.name }">
											${data.name }
										</c:when>
										<c:otherwise>
											N/A
										</c:otherwise>
									</c:choose>
								</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<td colspan="3">list is empty</td>
				</c:otherwise>
			</c:choose>
		</tbody>

	</table>
</body>
</html>