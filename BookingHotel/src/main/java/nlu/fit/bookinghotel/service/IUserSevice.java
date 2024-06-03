package nlu.fit.bookinghotel.service;

import nlu.fit.bookinghotel.DTO.HotelDTO;
import nlu.fit.bookinghotel.DTO.UserDTO;
import nlu.fit.bookinghotel.entity.Hotel;
import nlu.fit.bookinghotel.entity.User;
import nlu.fit.bookinghotel.exceptions.DataNotFoundException;

import java.util.List;

public interface IUserSevice {
    User createUser(UserDTO userDTO) throws Exception;
    User getUserById(long id);
    List<User> getAllUserl();
    User updateUser(long userId,UserDTO userDTO);
    void deleteUser(long id);
    String login(String phoneNumber,String password,Long roleId) throws Exception;
}
