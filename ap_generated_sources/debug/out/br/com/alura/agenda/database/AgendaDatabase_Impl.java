package br.com.alura.agenda.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.database.dao.AlunoDAO_Impl;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.database.dao.TelefoneDAO_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AgendaDatabase_Impl extends AgendaDatabase {
  private volatile AlunoDAO _alunoDAO;

  private volatile TelefoneDAO _telefoneDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(6) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Aluno` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT, `email` TEXT, `momentoDeCadastro` INTEGER)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Telefone` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `numero` TEXT, `tipo` TEXT, `alunoId` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '348cf6575d7f35a4a29e69a910b3b122')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Aluno`");
        _db.execSQL("DROP TABLE IF EXISTS `Telefone`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsAluno = new HashMap<String, TableInfo.Column>(4);
        _columnsAluno.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAluno.put("nome", new TableInfo.Column("nome", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAluno.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAluno.put("momentoDeCadastro", new TableInfo.Column("momentoDeCadastro", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAluno = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAluno = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAluno = new TableInfo("Aluno", _columnsAluno, _foreignKeysAluno, _indicesAluno);
        final TableInfo _existingAluno = TableInfo.read(_db, "Aluno");
        if (! _infoAluno.equals(_existingAluno)) {
          return new RoomOpenHelper.ValidationResult(false, "Aluno(br.com.alura.agenda.model.Aluno).\n"
                  + " Expected:\n" + _infoAluno + "\n"
                  + " Found:\n" + _existingAluno);
        }
        final HashMap<String, TableInfo.Column> _columnsTelefone = new HashMap<String, TableInfo.Column>(4);
        _columnsTelefone.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelefone.put("numero", new TableInfo.Column("numero", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelefone.put("tipo", new TableInfo.Column("tipo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelefone.put("alunoId", new TableInfo.Column("alunoId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTelefone = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTelefone = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTelefone = new TableInfo("Telefone", _columnsTelefone, _foreignKeysTelefone, _indicesTelefone);
        final TableInfo _existingTelefone = TableInfo.read(_db, "Telefone");
        if (! _infoTelefone.equals(_existingTelefone)) {
          return new RoomOpenHelper.ValidationResult(false, "Telefone(br.com.alura.agenda.model.Telefone).\n"
                  + " Expected:\n" + _infoTelefone + "\n"
                  + " Found:\n" + _existingTelefone);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "348cf6575d7f35a4a29e69a910b3b122", "0f13c4d72ace4b8feae3a121dc667a37");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Aluno","Telefone");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Aluno`");
      _db.execSQL("DELETE FROM `Telefone`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public AlunoDAO getRoomAlunoDAO() {
    if (_alunoDAO != null) {
      return _alunoDAO;
    } else {
      synchronized(this) {
        if(_alunoDAO == null) {
          _alunoDAO = new AlunoDAO_Impl(this);
        }
        return _alunoDAO;
      }
    }
  }

  @Override
  public TelefoneDAO getTelefoneDAO() {
    if (_telefoneDAO != null) {
      return _telefoneDAO;
    } else {
      synchronized(this) {
        if(_telefoneDAO == null) {
          _telefoneDAO = new TelefoneDAO_Impl(this);
        }
        return _telefoneDAO;
      }
    }
  }
}
