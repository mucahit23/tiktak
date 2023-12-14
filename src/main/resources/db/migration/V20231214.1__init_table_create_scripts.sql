CREATE SEQUENCE seq_question START WITH 1 INCREMENT BY 100;

CREATE TABLE questions (
 id BIGINT NOT NULL,
  question_text VARCHAR(255) NOT NULL,
  CONSTRAINT "pk_questions" PRIMARY KEY (id)
);

CREATE SEQUENCE seq_question_response START WITH 1 INCREMENT BY 100;

CREATE TABLE question_responses (
 id BIGINT NOT NULL,
  date_created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_updated TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  car_id BIGINT,
  question_id BIGINT NOT NULL,
  expertise_no int4,
  response BOOLEAN,
  description VARCHAR(255),
  CONSTRAINT "pk_quest?on_responses" PRIMARY KEY (id)
);

ALTER TABLE question_responses ADD CONSTRAINT FK_QUESTION_RESPONSES_ON_QUESTION_ID FOREIGN KEY (question_id) REFERENCES questions (id);

CREATE INDEX idx_expertise_no_car_id ON question_responses (expertise_no, car_id DESC);
CREATE INDEX idx_question_id ON question_responses (question_id);


CREATE SEQUENCE seq_response_image START WITH 1 INCREMENT BY 100;

CREATE TABLE response_images (
 id BIGINT NOT NULL,
  date_created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_updated TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  question_response_id BIGINT NOT NULL,
  image_url VARCHAR(255),
  CONSTRAINT "pk_response_images" PRIMARY KEY (id)
);

ALTER TABLE response_images ADD CONSTRAINT FK_RESPONSE_IMAGES_ON_QUESTION_RESPONSE_ID FOREIGN KEY (question_response_id) REFERENCES question_responses (id);

CREATE INDEX idx_question_response_id ON response_images (question_response_id);