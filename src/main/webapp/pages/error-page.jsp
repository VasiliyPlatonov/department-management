<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>

<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="error-wrapper">
                <p>This is error code: ${pageContext.errorData.statusCode}</p>
                <p><strong>${pageContext.errorData.throwable.message}</strong></p>
                <p>We are already trying to solve this problem.</p>
                <button onclick="history.back()">Back to Previous Page</button>
            </div>
        </div>
    </div>
</div>