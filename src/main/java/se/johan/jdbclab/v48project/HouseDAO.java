package se.johan.jdbclab.v48project;

public interface HouseDAO {
    void insertHouse(House house);
    void selectAllFromCity(String city);
}
