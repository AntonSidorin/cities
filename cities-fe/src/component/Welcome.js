import Login from 'component/auth/Login';

const Welcome = () => {
  return (
    <section className="welcome">
      <header>
        <h1>Welcome to Cities App!</h1>
      </header>
      <main>
        <div className="info">
          Cities with corresponding photo and possibility to search by name, edit city name and photo
        </div>
        <Login />
      </main>
    </section>
  );
};

export default Welcome;
