package example;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long>{
	Member findByUemail(String uemail);
}
