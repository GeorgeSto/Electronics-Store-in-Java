package database;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.BUY_ELECTRO;
import static database.Constants.Rights.CREATE_ELECTRO;
import static database.Constants.Rights.DELETE_ELECTRO;
import static database.Constants.Rights.RETURN_ELECTRO;
import static database.Constants.Rights.RIGHTS;
import static database.Constants.Rights.SELL_ELECTRO;
import static database.Constants.Rights.UPDATE_ELECTRO;
import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.CUSTOMER;
import static database.Constants.Roles.EMPLOYEE;
import static database.Constants.Roles.ROLES;
public class Constants {

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();
        for (String role : ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(CREATE_ELECTRO, DELETE_ELECTRO, UPDATE_ELECTRO, SELL_ELECTRO));

        rolesRights.get(CUSTOMER).addAll(Arrays.asList(SELL_ELECTRO, BUY_ELECTRO, RETURN_ELECTRO));

        return rolesRights;
    }

    public static class Schemas {
        public static final String TEST = "test_library";
        public static final String PRODUCTION = "library";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String ELECTRO = "electro";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String USER_ELECTRO = "user_electro";
        public static final String ORDERS = "orders";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,
                ELECTRO, USER_ELECTRO, ORDERS};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";
        public static final String CUSTOMER = "customer";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE, CUSTOMER};
    }

    public static class Rights {
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";

        public static final String CREATE_ELECTRO = "create_electro";
        public static final String DELETE_ELECTRO = "delete_electro";
        public static final String UPDATE_ELECTRO = "update_electro";

        public static final String SELL_ELECTRO = "sell_electro";
        public static final String BUY_ELECTRO = "buy_electro";
        public static final String RETURN_ELECTRO = "return_electro";

        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, CREATE_ELECTRO,
                DELETE_ELECTRO, UPDATE_ELECTRO, SELL_ELECTRO, BUY_ELECTRO, RETURN_ELECTRO};
    }
}