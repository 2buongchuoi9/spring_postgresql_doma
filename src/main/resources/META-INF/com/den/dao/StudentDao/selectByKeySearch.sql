select *
from student
where
    lower(name) like '%' || /*keySearch*/')))))))))))))))))))' || '%'
    or lower(code) like '%' || /*keySearch*/')))))))))))))))))))' || '%'
    or lower(address) like '%' || /*keySearch*/')))))))))))))))))))' || '%'
    or lower(email) like '%' || /*keySearch*/')))))))))))))))))))' || '%'
    or lower(phone) like '%' || /*keySearch*/')))))))))))))))))))' || '%'