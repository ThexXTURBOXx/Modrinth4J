package masecla.modrinth4j.endpoints.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.empty.EmptyRequest;
import masecla.modrinth4j.endpoints.generic.empty.EmptyResponse;
import masecla.modrinth4j.endpoints.user.ChangeUserIcon.ChangeUserIconRequest;
import masecla.modrinth4j.endpoints.user.GetUsers.GetUsersRequest;
import masecla.modrinth4j.endpoints.user.ModifyUser.ModifyUserRequest;
import masecla.modrinth4j.endpoints.user.ReportProjectUserOrVersion.ReportProjectUserOrVersionRequest;
import masecla.modrinth4j.endpoints.user.ReportProjectUserOrVersion.ReportProjectUserOrVersionResponse;
import masecla.modrinth4j.model.project.Project;
import masecla.modrinth4j.model.user.ModrinthUser;
import masecla.modrinth4j.model.user.ModrinthUserNotification;

@AllArgsConstructor
public class UserEndpoints {
    private Gson gson;
    private HttpClient client;

    /**
     * Gets a user by their ID or username
     * 
     * @param id - The ID or username of the user
     * @return - The user
     */
    public CompletableFuture<ModrinthUser> getUser(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new GetUser(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    /**
     * Gets multiple users by their IDs. <b> Note: This will only work with IDs, not
     * usernames. </b> unlike {@link #getUser(String)}
     * 
     * @param ids - The IDs of the users
     * @return - The users
     */
    public CompletableFuture<List<ModrinthUser>> getUser(String... ids) {
        return this.getUser(Arrays.asList(ids));
    }

    /**
     * Gets multiple users by their IDs. <b> Note: This will only work with IDs, not
     * usernames. </b> unlike {@link #getUser(String)}
     * 
     * @param ids - The IDs of the users
     * @return - The users
     */
    public CompletableFuture<List<ModrinthUser>> getUser(List<String> ids) {
        return new GetUsers(client, gson).sendRequest(new GetUsersRequest(ids));
    };

    public CompletableFuture<EmptyResponse> modifyUser(String id, ModifyUserRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new ModifyUser(client, gson).sendRequest(request, parameters);
    }

    public CompletableFuture<EmptyResponse> deleteUser(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new DeleteUser(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    public CompletableFuture<ModrinthUser> getSelf() {
        return new GetSelf(client, gson).sendRequest(new EmptyRequest(), new HashMap<>());
    }

    public CompletableFuture<EmptyResponse> changeProfilePicture(String id, File file) throws FileNotFoundException {
        return changeProfilePicture(id, new FileInputStream(file), file.getName());
    }

    public CompletableFuture<EmptyResponse> changeProfilePicture(String id, InputStream stream, String filename) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new ChangeUserIcon(client, gson).sendRequest(new ChangeUserIconRequest(filename, stream), parameters);
    }

    public CompletableFuture<List<Project>> getUserProjects(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new GetUserProjects(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    public CompletableFuture<List<Project>> getUserFollowedProjects(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new GetUserFollowedProjects(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    public CompletableFuture<List<ModrinthUserNotification>> getNotifications(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        return new GetUserNotifications(client, gson).sendRequest(new EmptyRequest(), parameters);
    }

    public CompletableFuture<ReportProjectUserOrVersionResponse> reportProjectUserOrVersion(
            ReportProjectUserOrVersionRequest request) {
        return new ReportProjectUserOrVersion(client, gson).sendRequest(request);
    }
}
