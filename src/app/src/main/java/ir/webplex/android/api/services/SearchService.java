package ir.webplex.android.api.services;

import ir.webplex.android.api.requests.FindDetailsRequest;
import ir.webplex.android.api.requests.FindLettersRequest;
import ir.webplex.android.api.requests.FindReceiversRequest;
import ir.webplex.android.api.requests.FindReceptorsRequest;
import ir.webplex.android.api.requests.SubmitReceiptRequest;
import ir.webplex.android.api.responses.FindDetailsResponse;
import ir.webplex.android.api.responses.FindLettersResponse;
import ir.webplex.android.api.responses.FindReceiversResponse;
import ir.webplex.android.api.responses.FindReceptorsResponse;
import ir.webplex.android.api.responses.SubmitReceiptResponse;
import ir.webplex.android.core.Constants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SearchService {
    @POST(Constants.API_URIS_SEARCH_FIND_LETTERS)
    public Call<FindLettersResponse> findLetters(@Body FindLettersRequest request);

    @POST(Constants.API_URIS_SEARCH_FIND_DETAILS)
    public Call<FindDetailsResponse> findDetails(@Body FindDetailsRequest request);

    @POST(Constants.API_URIS_SEARCH_FIND_RECEIVERS)
    public Call<FindReceiversResponse> findReceivers(@Body FindReceiversRequest request);

    @POST(Constants.API_URIS_SEARCH_SUBMIT_RECEIPT)
    public Call<SubmitReceiptResponse> submitReceipt(@Body SubmitReceiptRequest request);

    @POST(Constants.API_URIS_SEARCH_FIND_RECEPTORS)
    public Call<FindReceptorsResponse> findReceptors(@Body FindReceptorsRequest request);
}
