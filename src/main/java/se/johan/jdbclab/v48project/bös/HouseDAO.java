package se.johan.jdbclab.v48project.bös;

public interface HouseDAO {
    void insertHouse(House house);
    void selectAllHouseFromCity(String city);
    void selectAllFromHouse();
    void selectAllFromHouseAndPerson(int personId);
}
