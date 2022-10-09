package kz.iitu.issuetracker.feature.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppProfile {
    public static final String TEST = "test";
    public static final String DEVELOPMENT = "dev";
    public static final String PRODUCTION = "prod";
}
