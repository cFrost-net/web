<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat,java.util.Date" %>
            &nbsp;&nbsp;request.getRequestURL(): <%=request.getRequestURL() %><br/>
            &nbsp;&nbsp;request.getRequestURI(): <%=request.getRequestURI() %><br/>
            &nbsp;&nbsp;requestScope.requestURL: ${requestScope.requestURL}<br/>
            &nbsp;&nbsp;requestScope.requestURI: ${requestScope.requestURI}<br/>
            &nbsp;&nbsp;chinese test: ${requestScope.chinese}<br/>
            </div>
        </div>
        <footer>&copy;<%=new SimpleDateFormat("yyyy").format(new Date()) %>&nbsp;cFrost&nbsp;<a id="icp"
                href="http://www.miitbeian.gov.cn" target="_Blank">粤ICP备15054669号</a>
        </footer>
    </div>
</body>
</html>