package ifbank.Registro;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegistroController {
    
    public static ModelAndView list(Request req, Response res) {

        RegistroDao registroDao = new RegistroDao();
        int userId = req.session().attribute("user_id");

        Map<String, Object> model = new HashMap<>();
        model.put("registros", registroDao.listRegistrosById(userId)); 
        return new ModelAndView(model, "userPage.hbs");
        
    }

    public static ModelAndView detail(Request req, Response res) {

        int id = Integer.parseInt(req.params("id"));
        System.out.println(id);

        RegistroDao registroDao = new RegistroDao();
        Registro registro = registroDao.getById(id); //getById() retorna o objeto com todos seu atributos ja definidos

        Map<String, Object> model = new HashMap<>();
        model.put("registro", registro); // chave <-> valor, o valor nesse caso é o objeto entao dentro do hbs vamos
                                         // poder acessar seus atributos.
        return new ModelAndView(model, "registroDetail.hbs");
    };

    public static Route edit = (req, res) -> {

        int id = Integer.parseInt(req.params("id"));
        String description = req.queryParams("description");
        String string_valor = req.queryParams("valor");
        float valor = Float.parseFloat(string_valor);
        String tipo = req.queryParams("tipo");

        RegistroDao registroDao = new RegistroDao();
        Registro registro = registroDao.getById(id);

        registro.setDescription(description);
        registro.setValor(valor);
        registro.setTipo(tipo);

        registroDao.update(registro);

        res.redirect("/userHome");
        return "";
    };

    public static Route delete = (req, res) -> {

        int id = Integer.parseInt(req.params("id"));

        RegistroDao registroDao = new RegistroDao();
        registroDao.delete(id);

        res.redirect("/userHome"); // redirect automatico para página

        return "";
    };

    public static ModelAndView listTipo(Request req, Response res) {

        RegistroDao userDao = new RegistroDao();
        String filtro = req.queryParams("filtro");

        int user_id = Integer.parseInt(req.session().attribute("user_id"));
        System.out.println(user_id);
        
        Map<String, Object> model = new HashMap<>();
        model.put("registros", userDao.getByType(user_id, filtro)); 
        return new ModelAndView(model, "registrosPorTipo.hbs");
    }

    public static ModelAndView listDate(Request req, Response res) {

        String data_inicial = req.queryParams("data_inicial");
        String data_final = req.queryParams("data_final");
        
        int user_id = Integer.parseInt(req.session().attribute("user_id"));
        RegistroDao userDao = new RegistroDao();
        System.out.println(user_id);
        
        Map<String, Object> model = new HashMap<>();
        model.put("registros", userDao.getByDate(user_id, data_inicial, data_final)); // getUsers() retorna uma lista 
        return new ModelAndView(model, "registrosPelaData.hbs");
    }

    public static ModelAndView create(Request req, Response res) { // tem que retornar uma rota

        int userId = req.session().attribute("user_id");
        String description = req.queryParams("description");
        String string_valor = req.queryParams("valor");
        float valor = Float.parseFloat(string_valor);
        String tipo = req.queryParams("tipo");

        Registro registro = new Registro(userId, description, valor, tipo);

        RegistroDao registroDao = new RegistroDao();
        registroDao.add(registro);

        Map<String, Object> model = new HashMap<>();
        model.put("registro", registro);

        return new ModelAndView(model, "newRegistro.hbs");

    };

}
