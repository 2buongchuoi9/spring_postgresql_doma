update student
set
    /*%if status != null*/
    status = /*status*/0,
    /*%elseif clazzId != null*/
    clazzId = /*clazzId*/0,
    id = /*students.id*/0
where id = /*students.id*/