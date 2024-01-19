
### demo_spring_postgres

# table: school

| STT | FIELD   | TYPE          | NOTE | NULL |
| --- | ------- | ------------- | ---- | ---- |
| 1   | id      | integer       |      |      |
| 2   | name    | nvarchar(100) |      |      |
| 3   | adrress | nvarchar(100) |      |      |
| 4   | email   | nvarchar(100) |      |      |
| 5   | logo    | nvarchar(100) |      |      |

# table: clazz

| STT | FIELD      | TYPE          | NOTE          | NULL |
| --- | ---------- | ------------- | ------------- | ---- |
| 1   | id         | integer       |               |      |
| 2   | name       | nvarchar(100) |               |      |
| 3   | code       | nvarchar(8)   |               |      |
| 4   | school_id  | integer       | ref(school)   |      |
| 5   | status     | boolen        | default(true) |      |
| 6   | date_start | date          |               |      |
| 7   | date_end   | date          |               |      |

# table: student

| STT | FIELD        | TYPE          | NOTE                                                    | NULL |
| --- | ------------ | ------------- | ------------------------------------------------------- | ---- |
| 1   | id           | integer       |                                                         |      |
| 2   | name         | nvarchar(100) |                                                         |      |
| 3   | clazz_id     | nvarchar(8)   | ref(class)                                              |      |
| 4   | birthday     | date          |                                                         |      |
| 5   | img          | nvarchar(100) |                                                         | x    |
| 6   | adrress      | nvarchar(100) |                                                         |      |
| 7   | email        | nvarchar(100) |                                                         | x    |
| 8   | phone        | nvarchar(13)  |                                                         | x    |
| 9   | status       | integer       | enum("active","out","in","off","end") default("active") |      |
| 10  | verify_email | boolean       | defaule(false)                                          | x    |

table: history_transfer_student

| STT | FIELD       | TYPE    | NOTE         | NULL |
| --- | ----------- | ------- | ------------ | ---- |
| 1   | id          | integer |              |      |
| 2   | student_id  | integer | ref(student) |      |
| 3   | school_from | integer | ref(school)  |      |
| 4   | school_to   | integer | ref(school)  |      |
| 5   | from_date   | date    |              |      |
| 6   | to_date     | date    |              |      |

