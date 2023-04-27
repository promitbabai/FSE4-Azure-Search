package com.iiht.fse4.skilltrackersearch.repo;

import com.iiht.fse4.skilltrackersearch.entity.Associate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssociateRepository extends MongoRepository<Associate, String> {

   // @Autowired
   // MongoTemplate mt;

    public Associate findByName(String firstName);

    public Associate findByAssociateid(String associateId);

    public List<Associate> findByOrderByAssociateidAsc();

    public List<Associate> findByOrderByAssociateidDesc();

    public List<Associate> findByOrderByNameAsc();

    public List<Associate> findByOrderByNameDesc();


    @Query("{name : ?0}") // SQL Equivalent : SELECT * FROM ASSOCIATES where name = ?
    List<Associate> getAssociatesByName(String name);

    @Query(value = "{ 'technical_skills': { $elemMatch: {'topic' :?0, $elemMatch: {'rating' : {$gt : 10}} } }")
    public List<Associate> getAssociateBySkill(String topic );



    // SQL Equivalent : SELECT * FROM ASSOCIATE where technical_skills.topic = ? and technical_skills.rating=?
    //@Query("{$and:[{technical_skills.topic: ?0}, {technical_skills.rating: $gt: ?1}]}")
    //@Query(value = "{'technical_skills.topic' : ?0, 'technical_skills.rating' : {$gte : ?1}}")
    //@.Query(value = "{ 'bookletSignups': { $elemMatch: { 'bookletId' : ?0 } }}")


    //@Query(value = "{'companyCode' : ?0, 'currentDt' : {$gt : ?1, $lt : ?2}}")
    //public List<Stock> findStocksByCompanyCode(@Param("companyCode") String companyCode, @Param("startdate") Date startdate, @Param("enddate") Date enddate);

    //@Query("select a from associate where a.technical_skills.topic=: topic and a.technical_skills.rating >: 10")
    //public List<Associate> getAssociateBySkill(@Param("topic") String topic, final String rating);

    //@Query("select c from Company c where c.companyCode=:companyCode")
    //Company findByCompanyCode(@Param("companyCode") String companyCode);

    //@Query("select s from Stock s where s.companyCode=:companyCode and s.currentFlag = true")
    //public Stock findByCompanyCode(@Param("companyCode") String companyCode);

    //@Query("select s from Stock s where s.companyCode=:companyCode and s.currentDt BETWEEN :startdate AND :enddate")
    //public List<Stock> getAllStocksForCompany(@Param("companyCode") String companyCode, @Param("startdate") Date startdate, @Param("enddate") Date enddate);

    //@Query("select s from Stock s where s.companyCode=:companyCode")
    //public  List<Stock> deleteByCompanyCode(@Param("companyCode")  String companyCode);


    //--------------------------------custom query methods------------------------

//    @Query("{id :?0}")                                                  //SQL Equivalent : SELECT * FROM BOOK WHERE ID=?
//    Optional<Book> getBookById(Integer id);
//
//    @Query("{pages : {$lt: ?0}}")                                 // SQL Equivalent : SELECT * FROM BOOK where pages<?
//    @Query("{ pages : { $gte: ?0 } }")                        // SQL Equivalent : SELECT * FROM BOOK where pages>=?
//    @Query("{ pages : ?0 }")                                      // SQL Equivalent : SELECT * FROM BOOK where pages=?
//    List<Book> getBooksByPages(Integer pages);
//
//
//    @Query("{author : ?0}")                                         // SQL Equivalent : SELECT * FROM BOOK where author = ?
//    List<Book> getBooksByAuthor(String author);
//
//
//    @Query("{author: ?0, cost: ?1}")                            // SQL Equivalent : SELECT * FROM BOOK where author = ? and cost=?
//    @Query("{$and :[{author: ?0},{cost: ?1}] }")
//    List<Book> getBooksByAuthorAndCost(String author, Double cost);
//
//
//    @Query("{$or :[{author: ?0},{name: ?1}]}")            //SQL Equivalent : select count(*) from book where author=? or name=?
//    List<Book> getBooksByAuthorOrName(String author, String name);
//
//
//    @Query(value ="{author: ?0}", count=true)           //SQL Equivalent : select count(*) from book where author=?
//    Integer getBooksCountByAuthor(String author);
//
//    //Sorting
//    @Query(value = "{author:?0}", sort= "{name:1}") //ASC
//    @Query(value = "{author=?0}", sort= "{name:-1}") //DESC
//    List<Book> getBooksByAuthorSortByName(String author);
//
//
//    //------------------- @Query with Projection ---------------------------------------
//    @Query(value= "{pages: ?0}", fields="{name:1, author:1}")   // only data of name & author properties will be displayed
//    @Query(value= "{pages: ?0}", fields="{name:1, author:1, cost:1, pages:1}") // will display all properties data
//    List<Book> getBookNameAndAuthorByPages(Integer pages);
//
//
//    @Query(value= "{author : ?0}")           // SQL Equivalent : SELECT * FROM BOOK select * from books where author=?
//    List<Book> getAllBooksByAuthor(String author);
//
//    //------------------MongoDB Regular Expressions--------------------------------------
//    @Query("{ author : { $regex : ?0 } }")
//    List<Book> getBooksByAuthorRegEx(String author);


}




