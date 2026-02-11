package com.lidigu.coreDatabase

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Long
import kotlin.String

public class AppDatabaseQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> getGameById(id: Long, mapper: (
    id: Long,
    image: String,
    name: String,
    isFavorite: Long,
    isDownloaded: Long,
    rating: Long?,
    review: String?,
  ) -> T): Query<T> = GetGameByIdQuery(id) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!,
      cursor.getLong(5),
      cursor.getString(6)
    )
  }

  public fun getGameById(id: Long): Query<Game> = getGameById(id) { id_, image, name, isFavorite,
      isDownloaded, rating, review ->
    Game(
      id_,
      image,
      name,
      isFavorite,
      isDownloaded,
      rating,
      review
    )
  }

  public fun <T : Any> getAllGames(mapper: (
    id: Long,
    image: String,
    name: String,
    isFavorite: Long,
    isDownloaded: Long,
    rating: Long?,
    review: String?,
  ) -> T): Query<T> = Query(-624_341_477, arrayOf("game"), driver, "AppDatabase.sq", "getAllGames",
      "SELECT game.id, game.image, game.name, game.isFavorite, game.isDownloaded, game.rating, game.review FROM game") {
      cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!,
      cursor.getLong(5),
      cursor.getString(6)
    )
  }

  public fun getAllGames(): Query<Game> = getAllGames { id, image, name, isFavorite, isDownloaded,
      rating, review ->
    Game(
      id,
      image,
      name,
      isFavorite,
      isDownloaded,
      rating,
      review
    )
  }

  /**
   * @return The number of rows updated.
   */
  public fun insertGame(
    id: Long?,
    image: String,
    name: String,
    isFavorite: Long,
    isDownloaded: Long,
    rating: Long?,
    review: String?,
  ): QueryResult<Long> {
    val result = driver.execute(128_589_478, """
        |INSERT OR IGNORE INTO game(id, image, name, isFavorite, isDownloaded, rating, review)
        |VALUES (?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 7) {
          bindLong(0, id)
          bindString(1, image)
          bindString(2, name)
          bindLong(3, isFavorite)
          bindLong(4, isDownloaded)
          bindLong(5, rating)
          bindString(6, review)
        }
    notifyQueries(128_589_478) { emit ->
      emit("game")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun updateFavorite(isFavorite: Long, id: Long): QueryResult<Long> {
    val result = driver.execute(1_607_174_240, """UPDATE game SET isFavorite = ? WHERE id = ?""", 2)
        {
          bindLong(0, isFavorite)
          bindLong(1, id)
        }
    notifyQueries(1_607_174_240) { emit ->
      emit("game")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun updateGameDetails(
    image: String,
    name: String,
    id: Long,
  ): QueryResult<Long> {
    val result = driver.execute(-1_915_624_276,
        """UPDATE game SET image = ?, name = ? WHERE id = ?""", 3) {
          bindString(0, image)
          bindString(1, name)
          bindLong(2, id)
        }
    notifyQueries(-1_915_624_276) { emit ->
      emit("game")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun updateDownloaded(isDownloaded: Long, id: Long): QueryResult<Long> {
    val result = driver.execute(-146_804_501, """UPDATE game SET isDownloaded = ? WHERE id = ?""",
        2) {
          bindLong(0, isDownloaded)
          bindLong(1, id)
        }
    notifyQueries(-146_804_501) { emit ->
      emit("game")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun updateReview(
    rating: Long?,
    review: String?,
    id: Long,
  ): QueryResult<Long> {
    val result = driver.execute(1_939_969_820,
        """UPDATE game SET rating = ?, review = ? WHERE id = ?""", 3) {
          bindLong(0, rating)
          bindString(1, review)
          bindLong(2, id)
        }
    notifyQueries(1_939_969_820) { emit ->
      emit("game")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun delete(id: Long): QueryResult<Long> {
    val result = driver.execute(1_727_826_822, """DELETE FROM game WHERE id = ?""", 1) {
          bindLong(0, id)
        }
    notifyQueries(1_727_826_822) { emit ->
      emit("game")
    }
    return result
  }

  private inner class GetGameByIdQuery<out T : Any>(
    public val id: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("game", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("game", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(125_401_951,
        """SELECT game.id, game.image, game.name, game.isFavorite, game.isDownloaded, game.rating, game.review FROM game WHERE id = ?""",
        mapper, 1) {
      bindLong(0, id)
    }

    override fun toString(): String = "AppDatabase.sq:getGameById"
  }
}
