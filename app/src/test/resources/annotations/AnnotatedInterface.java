@interface RemoteServiceRelativePath {
    String value();
}

interface RemoteService {
}

@RemoteServiceRelativePath("Unite")
interface IUniteService extends RemoteService {
}
