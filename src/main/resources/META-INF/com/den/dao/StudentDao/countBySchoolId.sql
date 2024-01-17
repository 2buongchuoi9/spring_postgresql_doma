select count(s.*)
from student s
         join clazz c on s.clazz_id = c.id
         join school sc on c.school_id = sc.id
where sc.id = /*schoolId*/0
    /*%if status != null*/
  and s.status = /*status*/0
/*%end*/