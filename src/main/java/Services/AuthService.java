package Services;

import Domain.Dtos.RequestDto;
import Domain.Dtos.ResponseDto;
import Domain.Dtos.auth.LoginRequestDto;
import Domain.Dtos.auth.UserResponseDto;
import java.io.IOException;

public class AuthService extends BaseService {
    public AuthService(String host, int port) {
        super(host, port);
    }

    public UserResponseDto login(String usernameOrEmail, String password) throws IOException {
        LoginRequestDto loginDto = new LoginRequestDto(usernameOrEmail, password);
        RequestDto request = new RequestDto(
                "Auth",
                "login",
                gson.toJson(loginDto),
                null);

        ResponseDto response = sendRequest(request);
        return gson.fromJson(response.getData(), UserResponseDto.class);
    }
}
