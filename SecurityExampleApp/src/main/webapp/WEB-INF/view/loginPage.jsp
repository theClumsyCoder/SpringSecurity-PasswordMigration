<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Please Login</title>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
  $(function () {
  	$("#username").focus();
  });
</script>
</head>
<body>
  <h2>Custom Login Page</h2>
  <c:url value="/loginPage" var="loginUrl" />
  <form action="${loginUrl}" method="post">
    <c:if test="${param.error != null}">
      <p>Invalid username and password.</p>
    </c:if>
    <c:if test="${param.logout != null}">
      <p>You have been logged out.</p>
    </c:if>
    <p>
      <label for="username">Username</label> <input type="text"
        id="username" name="username" />
    </p>
    <p>
      <label for="password">Password</label> <input type="password"
        id="password" name="password" />
    </p>
    <input type="hidden" name="${_csrf.parameterName}"
      value="${_csrf.token}" />
    <button type="submit" class="btn">Log in</button>
  </form>
</body>
</html>