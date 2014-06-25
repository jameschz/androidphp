<?php
/**
 * Global settings
 */
require_once 'global.defines.php';

/**
 * Session settings
 */
require_once 'global.session.php';

/**
 * Datamap settings
 */
require_once 'global.datamap.php';

/**
 * Message settings
 */
require_once 'global.message.php';

/**
 * Database settings
 */
require_once 'database.mysql.php';

/**
 * App definitions
 */
define('__APP_NAME', 'Demos');
define('__APP_VERSION', '1.0');

/**
 * URL relative constants
 */
define('__HOST_SERVER', 'http://10.0.2.2:8001');
define('__HOST_WEBSITE', 'http://10.0.2.2:8002');

/**
 * MVC url mapping ini file
 */
define('__MAP_INI_FILE', realpath(__ETC . '/app.mapping.ini'));

/**
 * Logic libraries
 */
define('__LIB_PATH_CLI', realpath(__LIB_DIR . '/Demos/Cli'));
define('__LIB_PATH_SERVER', realpath(__LIB_DIR . '/Demos/App/Server'));
define('__LIB_PATH_WEBSITE', realpath(__LIB_DIR . '/Demos/App/Website'));

/**
 * Data dir settings
 */
define('__DAT_LOG_DIR', realpath(__DAT_DIR . '/log'));
define('__DAT_CACHE_DIR', realpath(__DAT_DIR . '/cache'));
