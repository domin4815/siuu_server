package siuu;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by domin4815 on 23.05.16.
 */
@Repository
public interface IUserRepository extends CrudRepository<User, Integer>{
}
