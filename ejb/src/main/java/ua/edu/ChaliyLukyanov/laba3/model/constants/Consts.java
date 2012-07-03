package ua.edu.ChaliyLukyanov.laba3.model.constants;

public class Consts {

    //component atributes
    public static final String PRICE = "price";
    public static final String IMG = "img";
    public static final String WEIGHT = "weight";
    public static final String PRODUCER = "producer";
    public static final String DESCRIPTION = "description";
    public static final String TITLE = "title";
    public static final String ID_COMPONENT = "id_component";

    //messages

    public static final String CANT_CLOSE_CONNECTION = "Can't close connection";
    public static final String CANT_CLOSE_STATEMENT = "Can't close statement";
    public static final String CANT_CLOSE_RESULT_SET = "Cant' close result set";
    public static final String CANT_FIND_COMPONENTS = "Can't get components from database";
    public static final String CANT_GET_COMPONENT = "Can't get component from database";
    public static final String CANT_LOAD_COMPONENT = "Can't load component";
    public static final String CANT_REMOVE_COMPONENT = "Can't remove component";
    public static final String CANT_STORE_COMPONENT = "Can't store component";
    public static final String CANT_CREATE_COMPONENT = "Can't add component to database";
    public static final String CANT_GET_PRODUCERS = "Can't get producers from database";
    
    
    public static final String CANT_FIND_DEVICES = "Can't get devices from database";
    public static final String CANT_CREATE_DEVICE = "Can't create device to database";
    public static final String NO_SUCH_DEVICE = "No such device in database!";
    public static final String CANT_GET_DEVICE_ID = "Can't get device's id";
    public static final String CANT_LOAD_DEVICE = "Can't load device";
    public static final String CANT_REMOVE_DEVICE = "Can't remove device";

    public static final String DB_NAME = "java:/OracleDS";

    //component sql
    public static final String GET_ALL_COMPONENTS = "select * from component";
    public static final String GET_DISTINCT_PRODUCERS = "select distinct producer from component";
    public static final String GET_COMPONENT_BY_ID = "select * from component where id_component = ?";
    public static final String INSERT_COMPONENT = "insert into component values(com_sq.nextval,?,?,?,?,?,?)";
    public static final String REMOVE_COMPONENT = "delete from component where id_component = ?";
    public static final String UPDATE_COMPONENT =
            "update component set title = ?, description = ?, producer = ?, "
            + "weight = ?, img = ?, price = ? where id_component = ?";
    public static final String GET_ID_LAST_COMPONENT = "select com_sq.currval from dual";

    //device atributes
    public static final String LEVEL = "level";
    public static final String ID_PREV = "id_prev";
    public static final String ID_DEVICE = "id_device";

    //device sql
    public static final String GET_ALL_DEVICES = "select level, id_device, id_component, id_prev, title from device start with id_prev is null connect by prior id_device=id_prev";
    public static final String GET_DEVICE_BY_ID = "select * from device where id_device = ?";
    public static final String INSERT_DEVICE = "insert into device values(dev_sq.nextval,?,?,?)";
    public static final String REMOVE_DEVICE = "delete from device where id_device = ?";
    public static final String GET_FIRST_LEVEL_DEVICES = "select id_device, id_component, id_prev, title from device where id_prev is null";
    public static final String GET_NEXT_LEVEL_DEVICE_BY_ID = "select level, id_device, id_component, id_prev, title from device where level = 1 start with id_prev = ? connect by prior id_device=id_prev";
    public static final String GET_PREV_LEVELS_DEVICE_BY_ID = "select level, id_device, id_component, id_prev, title from device where level > 1 start with id_device = ? connect by prior id_prev=id_device";
    public static final String GET_ID_LAST_DEVICE = "select dev_sq.currval from dual";
}
