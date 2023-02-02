package masecla.modrinth4j.endpoints.version.files;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.endpoints.generic.Endpoint;
import masecla.modrinth4j.endpoints.version.files.GetVersionByHash.GetVersionByHashRequest;
import masecla.modrinth4j.model.version.FileHash;
import masecla.modrinth4j.model.version.ProjectVersion;

public class GetVersionByHash extends Endpoint<ProjectVersion, GetVersionByHashRequest> {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetVersionByHashRequest {
        private FileHash algorithm;
    }

    public GetVersionByHash(HttpClient client, Gson gson) {
        super(client, gson);
    }

    @Override
    public String getEndpoint() {
        return "/version_file/{hash}";
    }

    @Override
    public TypeToken<GetVersionByHashRequest> getRequestClass() {
        return TypeToken.get(GetVersionByHashRequest.class);
    }

    @Override
    public TypeToken<ProjectVersion> getResponseClass() {
        return TypeToken.get(ProjectVersion.class);
    }

    @Override
    public boolean isJsonBody() {
        return false;
    }
}
