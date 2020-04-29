package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GetMutterLogic;
import model.Mutter;
import model.PostMutterLogic;
import model.User;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		setMutterListAttribute(request);

		// ログインしているか確認
		var session = request.getSession();
		var loginUser = session.getAttribute("loginUser");

		if(loginUser == null) {
			response.sendRedirect("/docoTsubu/");
			return;
		}

		forwardToMain(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		var text = (String)request.getParameter("text");

		addMutter(request, text);

		forwardToMain(request, response);
	}

	private void addMutter(HttpServletRequest request, String text) {

		if(text == null || text.length() == 0) {
			request.setAttribute("errorMsg", "つぶやきが入力されていません。");
			return;
		}

		var session = request.getSession();
		var loginUser = (User)session.getAttribute("loginUser");

		var mutter = new Mutter(loginUser.getName(), text);

		var postMutterLogic = new PostMutterLogic(getDbPath());
		postMutterLogic.execute(mutter);

		setMutterListAttribute(request);
	}

	private void forwardToMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}

	private void setMutterListAttribute(HttpServletRequest request) {
		GetMutterLogic getMutterLogic = new GetMutterLogic(getDbPath());
		List<Mutter> list = getMutterLogic.execute();
		request.setAttribute("mutterList", list);
	}

	private String getDbPath() {
		ServletContext context = this.getServletContext();
		return context.getRealPath("/WEB-INF/database/docoTsubu.db");
	}

}
