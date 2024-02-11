DROP TABLE IF EXISTS light_order CASCADE;
DROP TABLE IF EXISTS light CASCADE;
DROP TABLE IF EXISTS feature CASCADE;
DROP TABLE IF EXISTS light_order_lights CASCADE;
DROP TABLE IF EXISTS light_features CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;

CREATE TABLE light_order (
                             "id" BIGSERIAL PRIMARY KEY,
                             user_id BIGINT,
                             placed_at TIMESTAMP,
                             delivery_name VARCHAR(25),
                             delivery_street VARCHAR(25),
                             delivery_city VARCHAR(25),
                             delivery_state VARCHAR(25),
                             delivery_zip VARCHAR(25),
                             cc_number VARCHAR(25),
                             cc_expiration VARCHAR(25),
                             cc_cvv VARCHAR(25)
);

CREATE TABLE light (
                       "id" BIGSERIAL PRIMARY KEY,
                       "name" VARCHAR(50),
                       created_at TIMESTAMP
);

CREATE TABLE feature (
                         "id" BIGSERIAL PRIMARY KEY,
                         "name" VARCHAR(26),
                         flux VARCHAR(10),
                         "power" VARCHAR(10),
                         temperature VARCHAR(10),
                         "length" VARCHAR(10),
                         profile VARCHAR(10)
);

CREATE TABLE light_order_lights (
                                    light_order_id BIGINT,
                                    lights_id BIGINT
);

CREATE TABLE light_features (
                                light_id BIGINT,
                                features_id BIGINT
);

CREATE TABLE "user" (
                        "id" BIGSERIAL PRIMARY KEY,
                        username VARCHAR(26),
                        password VARCHAR(64),
                        fullName VARCHAR(35),
                        street VARCHAR(25),
                        city VARCHAR(25),
                        "state" VARCHAR(25),
                        zip VARCHAR(25),
                        phoneNumber VARCHAR(255)
);


ALTER TABLE light_order_lights
    ADD FOREIGN KEY (light_order_id) REFERENCES light_order("id");
ALTER TABLE light_order_lights
    ADD FOREIGN KEY (lights_id) REFERENCES light("id");
ALTER TABLE light_features
    ADD FOREIGN KEY (light_id) REFERENCES light("id");
ALTER TABLE light_features
    ADD FOREIGN KEY (features_id) REFERENCES feature("id");
