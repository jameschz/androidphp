<?php 
// session common settings
ini_set('session.name',                     'sid');	// session name
ini_set('session.auto_start',               0);
ini_set('session.gc_probability',           1);
ini_set('session.gc_divisor',               100);
ini_set('session.gc_maxlifetime',           3600);
ini_set('session.referer_check',            '');
ini_set('session.use_cookies',              0);		// don't use cookie
ini_set('session.use_only_cookies',         0);		// don't use cookie
ini_set('session.use_trans_sid',            1);		// use uri get parameter to pass session_id
ini_set('session.hash_function',            1);
ini_set('session.hash_bits_per_character',  5);