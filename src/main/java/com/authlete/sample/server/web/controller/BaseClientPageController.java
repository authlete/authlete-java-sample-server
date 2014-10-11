/*
 * Copyright 2014 Authlete, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.authlete.sample.server.web.controller;


import javax.ws.rs.core.Response;

import com.authlete.common.api.AuthleteApiException;
import com.authlete.common.dto.Client;
import com.authlete.common.dto.ClientListResponse;
import com.authlete.sample.server.authlete.Authlete;
import com.authlete.sample.server.util.ApiException;
import com.authlete.sample.server.util.RequestValidator;


abstract public class BaseClientPageController extends BasePageController
{
    private static final String CLIENT_DEVELOP_PAGE_TEMPLATE = "/user/client_develop.jsp";
    private static final String CLIENT_CREATE_PAGE_TEMPLATE  = "/user/client_create.jsp";
    private static final String CLIENT_UPDATE_PAGE_TEMPLATE  = "/user/client_update.jsp";
    private static final String CLIENT_DETAIL_PAGE_TEMPLATE  = "/user/client_detail.jsp";

    private static final String CLIENT_DEVELOP_PAGE_PATH     = "user/develop";
    private static final String CLIENT_DETAIL_PAGE_PATH      = "user/develop/%d/detail";


    protected BaseClientPageController(String path)
    {
        super(path);
    }


    //---------------------
    // API Call
    //---------------------

    public interface ApiCall<TResponse>
    {
        public TResponse call();
    }


    private <TResponse> TResponse callApi(ApiCall<TResponse> apiCall)
    {
        try
        {
            return apiCall.call();
        }
        catch (AuthleteApiException e)
        {
            e.printStackTrace();

            System.out.println(e.getStatusCode());
            System.out.println(e.getResponseBody());
            System.out.println(e.getStatusMessage());


            final String _message = e.getResponseBody();

            final String message =
                    _message == null ? "Failed to call Authlete's api" : _message;

            throw ApiException.badRequest(message);
        }
    }


    protected Client getClient(final long clientId)
    {
        RequestValidator.validateNotNegative("clientId", clientId);

        return callApi(new ApiCall<Client>() {
            @Override
            public Client call()
            {
                return Authlete.getApi().getClient(clientId);
            }
        });
    }


    protected ClientListResponse getClientList(final String userId)
    {
        RequestValidator.validateNotNullAndNotBlank("userId", userId);

        return callApi(new ApiCall<ClientListResponse>() {
            @Override
            public ClientListResponse call()
            {
                return Authlete.getApi().getClientList(userId);
            }
        });
    }


    protected Client createClient(final Client client)
    {
        RequestValidator.validateNotNull("client", client);

        return callApi(new ApiCall<Client>() {
            @Override
            public Client call()
            {
                return Authlete.getApi().createClient(client);
            }
        });
    }


    protected Client updateClient(final Client client)
    {
        RequestValidator.validateNotNull("client", client);

        return callApi(new ApiCall<Client>() {
            @Override
            public Client call()
            {
                return Authlete.getApi().updateClient(client);
            }
        });
    }


    protected void deleteClient(final long clientId)
    {
        RequestValidator.validateNotNegative("clinetId", clientId);

        callApi(new ApiCall<Void>() {
            @Override
            public Void call()
            {
                Authlete.getApi().deleteClient(clientId);

                return null;
            }
        });
    }


    //--------------------------
    // Response
    //--------------------------

    protected Response createResponseOfClientDevelopPage(ClientListResponse response)
    {
        return createResponse(CLIENT_DEVELOP_PAGE_TEMPLATE, response);
    }


    protected Response createResponseOfClientCreatePage()
    {
        return createResponse(CLIENT_CREATE_PAGE_TEMPLATE);
    }


    protected Response createResponseOfClientUpdatePage(Client client)
    {
        return createResponse(CLIENT_UPDATE_PAGE_TEMPLATE, client);
    }


    protected Response createResponseOfClientDetailPage(Client client)
    {
        return createResponse(CLIENT_DETAIL_PAGE_TEMPLATE, client);
    }


    protected Response createLocationResponseOfClientDevelopPage()
    {
        return createLocationResponse(CLIENT_DEVELOP_PAGE_PATH);
    }


    protected Response createLocationResponseOfClientDetailPage(Client client)
    {
        if (client == null)
        {
            throw new IllegalArgumentException("client is null.");
        }

        // Location of the page containing the detail information about the client.
        final String locationOfDatailPage =
                String.format(CLIENT_DETAIL_PAGE_PATH, client.getClientId());

        return createLocationResponse(locationOfDatailPage);
    }
}
