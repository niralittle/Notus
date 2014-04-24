<%--
    Document   : index
    Created on : 5 04 2014, 17:01:02
    Author     : Igor Lytvynenko
--%>

<%
	if (request.getUserPrincipal() != null) {
%>

<h3>You logged in as, <%=request.getUserPrincipal()%></h3>

<form method="GET" action="logout">
	<input type="submit" value="Logout" />
</form>

<%
	}
%>
  
<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac dolor nisi. Maecenas ut suscipit dui. Vivamus sagittis purus hendrerit metus lobortis congue. Nam imperdiet, tellus quis hendrerit ultrices, quam erat convallis magna, a rhoncus erat mi vel est. Etiam eleifend justo quis quam sodales pulvinar. Vivamus euismod purus lorem, elementum rutrum arcu tincidunt eu. Morbi volutpat, velit a consectetur aliquam, ipsum sem adipiscing nisi, in scelerisque risus dolor at lectus. Vivamus tempus vulputate mauris, et placerat libero laoreet ac. Nam euismod sagittis nibh, sed imperdiet nisi venenatis sed.

Integer sagittis a purus quis consectetur. Proin nisi leo, dignissim et convallis ac, lobortis eu nibh. Phasellus auctor, arcu non suscipit tincidunt, ligula diam aliquam diam, ut imperdiet mauris justo sed nisi. Etiam vulputate dolor nec tellus dapibus euismod. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus scelerisque leo eget luctus pretium. In nec varius ipsum, id bibendum velit. Vestibulum erat erat, mollis eget iaculis et, vehicula ac est.

Donec luctus convallis justo ut facilisis. Nulla porttitor sapien quis mauris mattis, id ornare nisl cursus. Curabitur ac vestibulum urna. Phasellus congue lacus magna, aliquam elementum nibh viverra nec. Phasellus sed arcu diam. Ut a condimentum magna. Pellentesque dolor odio, aliquet lobortis orci a, fermentum tincidunt eros. Sed porta iaculis leo in suscipit. Ut feugiat augue neque, ut vulputate risus venenatis in. Duis pharetra vitae nibh auctor pretium. Praesent pellentesque iaculis metus, non fringilla velit.

Nunc id iaculis nisl. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Maecenas elementum nunc non molestie malesuada. Quisque non sapien metus. Maecenas id odio arcu. Maecenas lacinia risus sed libero mollis pellentesque. Lorem ipsum dolor sit amet, consectetur adipiscing elit.

Ut dapibus semper hendrerit. Integer mauris massa, tincidunt a libero posuere, vestibulum dapibus sem. Suspendisse felis arcu, euismod in ultrices non, suscipit vel dolor. Proin urna nunc, accumsan a congue dictum, hendrerit in est. Praesent nec diam dui. Aliquam sit amet bibendum tellus. Donec pellentesque luctus mauris, sit amet aliquam urna consequat vel. In diam nunc, cursus et viverra nec, viverra sit amet nunc. Aenean sit amet dapibus tortor. Quisque pretium dapibus nisi quis malesuada. Interdum et malesuada fames ac ante ipsum primis in faucibus. Etiam ornare euismod sem et pharetra. Donec vel quam gravida, auctor quam at, fermentum dolor.
</p>
