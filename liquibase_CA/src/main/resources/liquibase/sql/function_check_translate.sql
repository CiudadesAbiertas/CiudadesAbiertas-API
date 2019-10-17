IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Translate]') AND type in (N'FN', N'IF',N'TF', N'FS', N'FT'))
	DROP FUNCTION [dbo].[Translate]