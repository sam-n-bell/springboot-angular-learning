CREATE SCHEMA IF NOT EXISTS app;

ALTER TABLE app.place
ADD COLUMN employee_id UUID;

ALTER TABLE app.place
ADD CONSTRAINT fk_place_employee
FOREIGN KEY (employee_id) REFERENCES employee(employee_id);

CREATE INDEX place_employee_idx
ON app.place (employee_id);