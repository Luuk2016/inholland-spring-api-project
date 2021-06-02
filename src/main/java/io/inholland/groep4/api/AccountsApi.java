/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.25).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.inholland.groep4.api;

import io.inholland.groep4.api.model.UserAccount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-31T22:24:07.069Z[GMT]")
@Validated
public interface AccountsApi {

    @Operation(summary = "Get accounts", description = "Get accounts", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Customers", "Employees" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting accounts successful", content = @Content(schema = @Schema(implementation = UserAccount.class))),

            @ApiResponse(responseCode = "400", description = "Bad input parameter(s)"),

            @ApiResponse(responseCode = "401", description = "JWT Bearer Token is missing or invalid"),

            @ApiResponse(responseCode = "403", description = "You are forbidden to view this content"),

            @ApiResponse(responseCode = "404", description = "Item not found") })
    @RequestMapping(value = "/accounts",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<UserAccount>> getAccounts();


    @Operation(summary = "Get specific account", description = "Get specific account", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Customers", "Employees" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting specific account successful", content = @Content(schema = @Schema(implementation = UserAccount.class))),

            @ApiResponse(responseCode = "400", description = "Bad input parameter(s)"),

            @ApiResponse(responseCode = "401", description = "JWT Bearer Token is missing or invalid"),

            @ApiResponse(responseCode = "403", description = "You are forbidden to view this content"),

            @ApiResponse(responseCode = "404", description = "Item not found") })
    @RequestMapping(value = "/accounts/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<UserAccount> getSpecificAccount(@Parameter(in = ParameterIn.PATH, description = "The user ID", required=true, schema=@Schema()) @PathVariable("id") Long id);


    @Operation(summary = "Create account", description = "Create account", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employees", "Customers" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creating account successful", content = @Content(schema = @Schema(implementation = UserAccount.class))),

            @ApiResponse(responseCode = "400", description = "Bad input parameter(s)"),

            @ApiResponse(responseCode = "401", description = "JWT Bearer Token is missing or invalid"),

            @ApiResponse(responseCode = "403", description = "You are forbidden to view this content"),

            @ApiResponse(responseCode = "404", description = "Item not found") })
    @RequestMapping(value = "/accounts",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<UserAccount> postAccount(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody UserAccount body);

}
