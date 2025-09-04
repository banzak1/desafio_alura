CREATE TABLE Task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    statement VARCHAR(255) NOT NULL,
    task_order BIGINT NOT NULL,
    courseId_id BIGINT NOT NULL,
    DTYPE VARCHAR(50) NOT NULL,
    CONSTRAINT fk_task_course FOREIGN KEY (courseId_id) REFERENCES Course(id)
);