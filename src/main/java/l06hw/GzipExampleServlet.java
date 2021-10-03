package l06hw;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class GzipExampleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setHeader("Content-Type", "text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        for (int i = 0; i < 35000; i++) {
            out.write("ababagalamaga-ababagalamaga-ababagalamaga-ababagalamaga-ababagalamaga-ababagalamaga-");
        }
    }
}
