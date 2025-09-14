

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA app;

CREATE TABLE app.place (
    place_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created TIMESTAMPTZ DEFAULT NOW() NOT NULL,
    location GEOMETRY(Point, 4326) NOT NULL
);

CREATE INDEX place_location_idx
ON app.place
USING GIST (location);