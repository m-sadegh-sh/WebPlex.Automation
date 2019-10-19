package ir.webplex.android.api.services;

import ir.webplex.android.api.requests.LoginRequest;
import ir.webplex.android.api.responses.LoginResponse;
import ir.webplex.android.core.Constants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticateService {
    @POST(Constants.API_URIS_AUTHENTICATE_LOGIN)
    public Call<LoginResponse> login(@Body LoginRequest request);
}
