package ua.edu.ChaliyLukyanov.laba3.model.EJB;

public class Consts {

	public static final String CANT_CLOSE_CONNECTION = "Can't close connection";

	public static final String CANT_CLOSE_STATEMENT = "Can't close statement";

	public static final String CANT_CLOSE_RESULT_SET = "Cant' close result set";

	
	
	public static final String CANT_GET_DEVICE_FROM_DB = "Can't show device from database";
	
	public static final String CANT_ADD_DEVICE_TO_DB = "Can't add device to database";
	
	public static final String DEVICE_IS_EMPTY = "Can't get devices from database. DataBase is empty.";
	
	public static final String CANT_GET_DEVICES_FROM_DB = "Can't get device from database";
	
	public static final String CANT_REMOVE_DEVICE_FROM_DB = "Can't remove device from database";
	
	
	
	public static final String CANT_ADD_COMPONENT_TO_DB = "Can't add component to database";
	
	public static final String COMPONENT_IS_EMPTY = "Can't get components from database. DataBase is empty.";
	
	public static final String CANT_GET_COMPONENT_FROM_DB = "Can't get component from database";
	
	public static final String CANT_GET_COMPONENTS_FROM_DB = "Can't get components from database";
	
	
	
	
	public static final String PRICE = "price";

	public static final String IMG = "img";

	public static final String WEIGHT = "weight";

	public static final String PRODUCER = "producer";

	public static final String DESCRIPTION = "description";

	public static final String TITLE = "title";

	public static final String ID_COMPONENT = "id_component";

	public static final String GET_ALL_COMPONENTS_ID = "select id_component from component";

	public static final String GET_DISTINCT_PRODUCERS = "select distinct producer from component";

	public static final String GET_COMPONENT_BY_ID = "select title, description, producer, weight, img, price from component where id_component = ?";

	public static final String INSERT_COMPONENT = "insert into component values(com_sq.nextval,?,?,?,?,?,?)";

	public static final String REMOVE_COMPONENT = "delete from component where id_component = ?";

	public static final String UPDATE_COMPONENT = "update component set title = ?, description = ?, producer = ?, price = ?, weight = ?, img = ? where id_component = ?";

	public static final String GET_ID_LAST_COMPONENT = "select max(id_component) from component";
	
	public static final String GET_ID_NEW_COMPONENT = "select com_sq.nextval from dual";
	
	public static final String FIND_BY_ID_COMPONENT = "select 1 from component where id_component = ?";

	
	
	public static final String FIND_BY_ID_DEVICE = "select 1 from device where id_device = ?";
	
	public static final String LEVEL = "level";

	public static final String ID_PREV = "id_prev";

//	public static final String ID_COMPONENT = "id_component";

	public static final String ID_DEVICE = "id_device";

	public static final String GET_ALL_DEVICES_ID = "select id_device from device start with id_prev is null connect by prior id_device=id_prev";

	public static final String GET_DEVICE_BY_ID = "select id_prev, id_component, title from device where id_device = ?";

	public static final String INSERT_DEVICE = "insert into device values(dev_sq.nextval,?,?,?)";

	public static final String REMOVE_DEVICE = "delete from device where id_device = ?";

	public static final String GET_FIRST_LEVEL_DEVICES = "select id_device, id_component, id_prev, title from device where id_prev is null";

	public static final String GET_NEXT_LEVEL_DEVICE_BY_ID = "select level, id_device, id_component, id_prev, title from device where level = 1 start with id_prev = ? connect by prior id_device=id_prev";

	public static final String GET_PREV_LEVELS_DEVICE_BY_ID = "select level, id_device, id_component, id_prev, title from device where level > 1 start with id_device = ? connect by prior id_prev=id_device";

	public static final String GET_ID_LAST_DEVICE = "select max(id_device) from device";
	
	public static final String UPDATE_DEVICE = "update device set title = ?, id_prev = ?, id_component = ? where id_device = ?";

}