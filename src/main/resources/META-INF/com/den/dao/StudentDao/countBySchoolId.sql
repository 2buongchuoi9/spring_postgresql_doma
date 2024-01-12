select count(s.*)
from student s
         join clazz c on s.clazz_id = c.id
         join school sc on c.school_id = sc.id
where sc.id = /*schoolId*/0;