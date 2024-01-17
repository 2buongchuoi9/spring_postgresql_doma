select *
from student
where clazz_id = /*clazzId*/0
    /*%if status != null */
  and status = /*status*/0
/*%end*/;