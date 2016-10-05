import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Profile extends HttpServlet {

    private static Configuration cfg;

    @Override
    public void init() {
        cfg = new Configuration(new Version("2.3.25"));
        cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/resources");
    }

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException {
        HttpSession s = rq.getSession();
        String user = (String) s.getAttribute("user");
        if (user != null) {
            try {
                Template template = cfg.getTemplate("profile.ftl");
                Map<String, Object> root = new HashMap<String, Object>();
                root.put("name", user);
                template.process(root, rs.getWriter());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            rs.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
