select *
from student
where
    unaccent(name) like '%' || unaccent(/*keySearch*/'_') || '%'
    or unaccent(code) like '%' || unaccent(/*keySearch*/'_') || '%'
    or unaccent(address) like '%' || unaccent(/*keySearch*/'_') || '%'
    or unaccent(email) like '%' || unaccent(/*keySearch*/'_') || '%'
    or unaccent(phone) like '%' || unaccent(/*keySearch*/'_') || '%'