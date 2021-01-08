package pers.xiaomuma.base.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pers.xiaomuma.base.db.config.BaseDbConfiguration;
import pers.xiaomuma.base.web.config.BaseWebConfiguration;


@Import({BaseDbConfiguration.class, BaseWebConfiguration.class})
@Configuration
public class BaseConfiguration {
}
