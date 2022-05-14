-- Place your queries here. Docs available https://www.hugsql.org/

-- :name get-user-by-id :? :1
-- :doc returns a user object by id, or nil if not present
SELECT *
FROM users
WHERE id = :id

-- :name insert-user<! 
-- :doc inserts a new user into the database
INSERT INTO users (id, name, email, password)
VALUES (:id, :name, :email, :password)