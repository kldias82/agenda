package br.com.alura.agenda.database.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import br.com.alura.agenda.database.converter.ConversorTipoTelefone;
import br.com.alura.agenda.model.Telefone;
import br.com.alura.agenda.model.TipoTelefone;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TelefoneDAO_Impl implements TelefoneDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Telefone> __insertionAdapterOfTelefone;

  private final ConversorTipoTelefone __conversorTipoTelefone = new ConversorTipoTelefone();

  private final EntityInsertionAdapter<Telefone> __insertionAdapterOfTelefone_1;

  public TelefoneDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTelefone = new EntityInsertionAdapter<Telefone>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Telefone` (`id`,`numero`,`tipo`,`alunoId`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Telefone value) {
        stmt.bindLong(1, value.getId());
        if (value.getNumero() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNumero());
        }
        final String _tmp;
        _tmp = __conversorTipoTelefone.paraString(value.getTipo());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        stmt.bindLong(4, value.getAlunoId());
      }
    };
    this.__insertionAdapterOfTelefone_1 = new EntityInsertionAdapter<Telefone>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Telefone` (`id`,`numero`,`tipo`,`alunoId`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Telefone value) {
        stmt.bindLong(1, value.getId());
        if (value.getNumero() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNumero());
        }
        final String _tmp;
        _tmp = __conversorTipoTelefone.paraString(value.getTipo());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        stmt.bindLong(4, value.getAlunoId());
      }
    };
  }

  @Override
  public void salva(final Telefone... telefones) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTelefone.insert(telefones);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void atualiza(final Telefone... telefones) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTelefone_1.insert(telefones);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Telefone buscaPrimeiroTelefoneDoAluno(final int alunoId) {
    final String _sql = "SELECT * FROM Telefone WHERE alunoId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, alunoId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNumero = CursorUtil.getColumnIndexOrThrow(_cursor, "numero");
      final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
      final int _cursorIndexOfAlunoId = CursorUtil.getColumnIndexOrThrow(_cursor, "alunoId");
      final Telefone _result;
      if(_cursor.moveToFirst()) {
        final String _tmpNumero;
        _tmpNumero = _cursor.getString(_cursorIndexOfNumero);
        final TipoTelefone _tmpTipo;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfTipo);
        _tmpTipo = __conversorTipoTelefone.paraTipoTelefone(_tmp);
        _result = new Telefone(_tmpNumero,_tmpTipo);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpAlunoId;
        _tmpAlunoId = _cursor.getInt(_cursorIndexOfAlunoId);
        _result.setAlunoId(_tmpAlunoId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Telefone> buscaTodosTelefonesDoAluno(final int alunoId) {
    final String _sql = "SELECT * FROM Telefone WHERE alunoId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, alunoId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNumero = CursorUtil.getColumnIndexOrThrow(_cursor, "numero");
      final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
      final int _cursorIndexOfAlunoId = CursorUtil.getColumnIndexOrThrow(_cursor, "alunoId");
      final List<Telefone> _result = new ArrayList<Telefone>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Telefone _item;
        final String _tmpNumero;
        _tmpNumero = _cursor.getString(_cursorIndexOfNumero);
        final TipoTelefone _tmpTipo;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfTipo);
        _tmpTipo = __conversorTipoTelefone.paraTipoTelefone(_tmp);
        _item = new Telefone(_tmpNumero,_tmpTipo);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpAlunoId;
        _tmpAlunoId = _cursor.getInt(_cursorIndexOfAlunoId);
        _item.setAlunoId(_tmpAlunoId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
