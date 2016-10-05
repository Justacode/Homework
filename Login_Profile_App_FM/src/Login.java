import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Login extends HttpServlet {

    private static Configuration cfg;
    private Map passwords;

    @Override
    public void init() {
        cfg = new Configuration(new Version("2.3.25"));
        cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/resources");
        passwords = new HashMap();
        passwords.put("martin", "123");
        passwords.put("Jackson", "1995");
        passwords.put("Godzilla", "argh");
    }

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException {
        Cookie[] cookies = rq.getCookies();
        HttpSession s = rq.getSession();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String pass = cookie.getValue();
                if (hasUser(name, pass)) {
                    s.setAttribute("user", name);
                }
            }
        }
        if (s.getAttribute("user") != null) {
            rs.sendRedirect("/profile");
        } else {
            try {
                String error = (String) rq.getSession().getAttribute("error_msg");
                Template template = cfg.getTemplate("login.ftl");
                error = error == null ? "" : error;
                Map templateData = new HashMap();
                templateData.put("error", error);
                template.process(templateData, rs.getWriter());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException {
        HttpSession s = rq.getSession();

        if (s.getAttribute("user") != null) {
            rs.sendRedirect("/profile");
        } else {
            String user = rq.getParameter("user");
            String pass = rq.getParameter("pass");
            String remember = rq.getParameter("remember");
            if (passwords.containsKey(user)) {
                if (passwords.get(user).equals(pass)) {
                    if (remember != null) {
                        Cookie cookie = new Cookie(user, pass);
                        cookie.setMaxAge(24 * 60 * 60);
                        rs.addCookie(cookie);
                    }
                    s.setAttribute("user", user);
                    rs.sendRedirect("/profile");
                } else {
                    rq.getSession().setAttribute("error_msg", "Uncorrect password for user \" " + user + "\"");
                    rs.sendRedirect("/login");
                }
            } else {
                rq.getSession().setAttribute("error_msg", "Uncorrect username: " + user);
                rs.sendRedirect("/login");
            }
        }
    }

    private boolean hasUser(String username, String pass) {
        return (passwords.containsKey(username) && passwords.get(username).equals(pass));
    }
}

